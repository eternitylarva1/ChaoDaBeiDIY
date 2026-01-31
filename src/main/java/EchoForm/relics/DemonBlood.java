package EchoForm.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;import basemod.abstracts.CustomCard;
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
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.BlackBlood;
import com.megacrit.cardcrawl.relics.SnakeRing;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class DemonBlood extends CustomRelic {
    public static final String ID = "echoForm:DemonBlood";
    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final String IMG = "echoFormResources/images/relics/DemonBlood.png";
    
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
        // 使用CardLibrary.getCurse()方法从游戏卡牌库中随机抽取诅咒牌
        for (int i = 0; i < 3; i++) {
            AbstractCard curse = CardLibrary.getCurse().makeCopy();
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(curse, (float) Settings.WIDTH /2, (float) Settings.HEIGHT /2));
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
            if (this.counter < 2) {
                addToBot((AbstractGameAction)new GainEnergyAction(1));
                this.counter++;
            }
        }
    }

    @Override
    public void atTurnStart() {
        this.counter = 0;
    }

    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(BlackBlood.ID);
    }
    @Override
    public AbstractRelic makeCopy() {
        return new DemonBlood();
    }

}
