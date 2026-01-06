package EchoForm.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Taurus extends CustomRelic {
    public static final String ID = "echoForm:Taurus";
    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final String IMG = "echoFormResources/images/relics/Taurus.png";
    
    private static final int BONUS_DAMAGE = 24;
    private boolean firstAttackDone = false;
    private boolean firstTurn = true;

    public Taurus() {
        super(ID, new Texture(Gdx.files.internal(IMG)), RelicTier.COMMON, LandingSound.HEAVY);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        this.firstAttackDone = false;
        this.firstTurn = true;
    }

    @Override
    public void atTurnStart() {
        this.firstTurn = false;
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        // 每场战斗中的第一次攻击造成24点额外伤害
        if (card.type == AbstractCard.CardType.ATTACK && !this.firstAttackDone) {
            this.flash();
            this.firstAttackDone = true;
            // 对目标造成24点额外伤害
            if (m != null && !m.isDeadOrEscaped()) {
                addToBot((AbstractGameAction)new DamageAction(m, new DamageInfo(AbstractDungeon.player, BONUS_DAMAGE, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            }
        }
    }

    @Override
    public boolean canPlay(AbstractCard card) {
        // 第一个回合不能打出攻击牌
        if (this.firstTurn && card.type == AbstractCard.CardType.ATTACK) {
            return false;
        }
        return true;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Taurus();
    }
}