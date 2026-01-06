package EchoForm.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.curses.CurseOfTheBell;
import com.megacrit.cardcrawl.cards.curses.Doubt;
import com.megacrit.cardcrawl.cards.curses.Injury;
import com.megacrit.cardcrawl.cards.curses.Normality;
import com.megacrit.cardcrawl.cards.curses.Pain;
import com.megacrit.cardcrawl.cards.curses.Parasite;
import com.megacrit.cardcrawl.cards.curses.Regret;
import com.megacrit.cardcrawl.cards.curses.Shame;
import com.megacrit.cardcrawl.cards.curses.Writhe;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;

public class DemonBlood extends CustomRelic {
    public static final String ID = "echoForm:DemonBlood";
    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final String IMG = "echoFormResources/images/relics/DemonBlood.png";
    
    private int energyGainedThisTurn = 0;

    public DemonBlood() {
        super(ID, new Texture(Gdx.files.internal(IMG)), RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        // 拾起时，向卡组中加入三张随机诅咒
        ArrayList<AbstractCard> curses = new ArrayList<>();
        curses.add(new CurseOfTheBell());
        curses.add(new Doubt());
        curses.add(new Injury());
        curses.add(new Normality());
        curses.add(new Pain());
        curses.add(new Parasite());
        curses.add(new Regret());
        curses.add(new Shame());
        curses.add(new Writhe());
        
        for (int i = 0; i < 3; i++) {
            AbstractCard curse = curses.get(AbstractDungeon.cardRandomRng.random(curses.size() - 1)).makeCopy();
            AbstractDungeon.effectList.add(new com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect(curse, true, false));
        }
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if (card.type == AbstractCard.CardType.CURSE || card.type == AbstractCard.CardType.STATUS) {
            this.flash();
            // 消耗这张牌
            addToBot((AbstractGameAction)new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
            // 抽一张牌
            addToBot((AbstractGameAction)new DrawCardAction(AbstractDungeon.player, 1));
            // 回复1点血量
            addToBot((AbstractGameAction)new HealAction(AbstractDungeon.player, AbstractDungeon.player, 1));
            // 获得1点能量（每回合最多两点）
            if (this.energyGainedThisTurn < 2) {
                addToBot((AbstractGameAction)new GainEnergyAction(1));
                this.energyGainedThisTurn++;
            }
        }
    }

    @Override
    public void atTurnStart() {
        this.energyGainedThisTurn = 0;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new DemonBlood();
    }
}
