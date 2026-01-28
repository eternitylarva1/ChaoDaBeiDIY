package EchoForm.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RitualPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class RitualFeatherCrown extends CustomRelic {
    public static final String ID = "echoForm:RitualFeatherCrown";
    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final String IMG = "echoFormResources/images/relics/RitualFeatherCrown.png";
    
    private static final int RITUAL_AMOUNT = 2;
    private boolean firstTurn = true;

    public RitualFeatherCrown() {
        super(ID, new Texture(Gdx.files.internal(IMG)), RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        // 战斗开始时获得2层仪式
        this.flash();
        this.firstTurn = true;
        this.counter = 0;
        // 使用原版RitualPower，每回合结束时增加2点力量
        addToBot((AbstractGameAction)new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new RitualPower(AbstractDungeon.player, 2, true), 2));
    }

    @Override
    public void atTurnStart() {
        this.firstTurn = false;
        this.counter ++;
    }

    @Override
    public boolean canPlay(AbstractCard card) {
        // 你在第一回合不能打出攻击牌
        if (this.counter==1 && card.type == AbstractCard.CardType.ATTACK) {
            return false;
        }
        return true;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new RitualFeatherCrown();
    }
}
