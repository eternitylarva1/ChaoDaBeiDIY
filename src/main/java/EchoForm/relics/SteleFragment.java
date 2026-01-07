package EchoForm.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.cards.red.Defend_Red;
import com.megacrit.cardcrawl.cards.red.Bash;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;

import java.util.ArrayList;

public class SteleFragment extends CustomRelic {
    public static final String ID = "echoForm:SteleFragment";
    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final String IMG = "echoFormResources/images/relics/SteleFragment.png";
    
    private static final int MAX_HP_BONUS = 4;
    private static final int GOLD_BONUS = 50;

    public SteleFragment() {
        super(ID, new Texture(Gdx.files.internal(IMG)), RelicTier.COMMON, LandingSound.HEAVY);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }


    @Override
    public void onEquip() {
        // 拾起时，获得4点生命上限，获得50金币，获得一组牌
        this.flash();
        AbstractDungeon.player.increaseMaxHp(MAX_HP_BONUS, true);
        addToBot((AbstractGameAction)new GainGoldAction(GOLD_BONUS));
        // 获得一组牌
    RewardItem rewardItem=new RewardItem();
        AbstractDungeon.cardRewardScreen.open(rewardItem.cards, rewardItem, "选择一张卡牌");
        AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new SteleFragment();
    }
}
