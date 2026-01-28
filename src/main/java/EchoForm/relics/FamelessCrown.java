package EchoForm.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class FamelessCrown extends CustomRelic {
    public static final String ID = "echoForm:FamelessCrown";
    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final String IMG = "echoFormResources/images/relics/FamelessCrown.png";
    
    public FamelessCrown() {
        super(ID, new Texture(Gdx.files.internal(IMG)), RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] ;
    }

    @Override
    public void atBattleStart() {
        this.flash();
        this.counter = 3;
    }

    @Override
    public void atTurnStart() {
        this.flash();
        // 每回合抽牌加1
        addToBot((AbstractGameAction)new DrawCardAction(AbstractDungeon.player, 1));
        // 能量加3，每回合获得能量逐渐减少，不会减少到零 //todo 询问作者效果，有疑问
        if (this.counter > 0) {
            addToBot((AbstractGameAction)new GainEnergyAction(this.counter));
            this.counter--;
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new FamelessCrown();
    }
}