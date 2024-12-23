package com.example.flipmatch.adapter.gameadapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flipmatch.R;
import com.example.flipmatch.game.InfoBox;
import com.example.flipmatch.game.ScoreAnimation;
import com.example.flipmatch.model.CardModel;
import com.example.flipmatch.model.GameModel;
import com.example.flipmatch.play.CongratsScreen;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardHolder> {
    private final ArrayList<CardModel> mData;
    private final ArrayList<EasyFlipView> flipCards;
    private final ArrayList<String> names;
    GameModel gameModel;
    Context context;
    TextView gameScore, animScore;
    int totalCard;
    String fragment_round_num;
    FragmentManager fragment;
    ScoreAnimation scoreAnimation;

    public CardAdapter(ArrayList<CardModel> mData, Context context, GameModel gameModel, TextView gameScore, TextView animScore, int totalCard, FragmentManager fragment, String fragment_round_num){
        this.mData = mData;
        this.context = context;
        this.gameModel = gameModel;
        this.gameScore = gameScore;
        this.animScore = animScore;
        this.totalCard = totalCard;
        this.fragment_round_num = fragment_round_num;
        this.fragment = fragment;
        flipCards = new ArrayList<>();
        names = new ArrayList<>();
        scoreAnimation = new ScoreAnimation();
    }

    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
        return new CardHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardHolder holder, int position) {
        CardModel model = mData.get(position);
        holder.getBackImage().setImageResource(model.getBack_img());
        holder.getFrontImage().setImageResource(model.getFront_img());
        Handler handler = new Handler();
        gameLogic(holder.getEasyFlipView(), handler, model, scoreAnimation);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void gameLogic(EasyFlipView flipView, Handler handler, CardModel model, ScoreAnimation scoreAnimation){
        flipView.setOnFlipListener((easyFlipView, newCurrentSide) -> {
            if(easyFlipView.isBackSide()){
                flipCards.add(easyFlipView);
                names.add(model.getName());
                easyFlipView.setFlipOnTouch(false);
            } else {
                easyFlipView.setFlipOnTouch(true);
            }

            if (flipCards.size() == 2) {
                if(names.get(0).equals(names.get(1))){
                    totalCard--;
                    scoreAnimation.animationScore(animScore, "+10");
                    gameModel.setScore(+10);

                    handler.postDelayed(() -> {
                        for (EasyFlipView view : flipCards) {
                            view.setFlipEnabled(false);
                        }
                        flipCards.clear();
                        names.clear();
                    }, 200);
                } else {
                    scoreAnimation.animationScore(animScore, "-5");
                    gameModel.setScore(-5);
                    handler.postDelayed(() -> {
                        for (EasyFlipView view : flipCards) {
                            view.flipTheView();
                        }
                        flipCards.clear();
                        names.clear();
                    }, 200);
                }
            }
            scoreAnimation.delaySetText(gameScore, String.valueOf(gameModel.getScore()));
            //gameScore.setText(String.valueOf(gameModel.getScore()));

            if(totalCard == 0){
                handler.postDelayed(() -> {
                    if(fragment_round_num.equals("Round 1")){
                        fragment.beginTransaction().replace(R.id.fragment_container, new CongratsScreen(gameModel, "Round 1")).commit();
                    } else if(fragment_round_num.equals("Round 2")){
                        InfoBox infoBox = new InfoBox();
                        infoBox.addNameScore(context, String.valueOf(gameModel.getScore()));
                        fragment.beginTransaction().replace(R.id.fragment_container, new CongratsScreen(gameModel, "Round 2")).commit();
                    }
                }, 800);
            }
        });
    }
}
