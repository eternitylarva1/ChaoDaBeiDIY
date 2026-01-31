package EchoForm.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import EchoForm.actions.HolyRingAction;
import com.megacrit.cardcrawl.relics.BlackBlood;
import com.megacrit.cardcrawl.relics.SnakeRing;

public class HolyRing extends CustomRelic {
    public static final String ID = "echoForm:HolyRing";
    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final String IMG = "echoFormResources/images/relics/HolyRing.png";
    
    public HolyRing() {
        super(ID, new Texture(Gdx.files.internal(IMG)), RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atTurnStartPostDraw() {
        this.flash();

        this.counter = 0;
        // 每回合开始时，额外抽两张牌
        addToBot((AbstractGameAction)new DrawCardAction(AbstractDungeon.player, 2));
        
        // 然后可以弃置任意张牌，获得等量的能量（每回合最多两点能量）
        if (!AbstractDungeon.player.hand.isEmpty()) {
            addToBot((AbstractGameAction)new WaitAction(0.5f));
            // 使用HolyRingAction让玩家选择要弃置的牌，每弃置一张获得1点能量（最多2点）
            addToBot((AbstractGameAction)new HolyRingAction(AbstractDungeon.player, 2));
        }
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        // 当抽到牌时，可以选择弃置它来获得能量
        // 由于atTurnStart已经实现了弃牌获得能量的功能，这里不再需要额外实现
        // 玩家可以在回合开始时选择弃置任意数量的牌来获得能量
    }
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(SnakeRing.ID);
    }


    @Override
    public void onExhaust(AbstractCard card) {
        // 当弃置牌时，获得能量（每回合最多两点）
        if (this.counter < 2) {
            this.flash();
            addToBot((AbstractGameAction)new GainEnergyAction(1));
            this.counter++;
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new HolyRing();
    }
}
