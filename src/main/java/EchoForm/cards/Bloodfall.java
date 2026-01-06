package EchoForm.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Bloodfall extends AbstractCard {
    public static final String ID = "echoForm:Bloodfall";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "echoFormResources/images/cards/Bloodfall.png";
    private static final int COST = -1; // X费

    public Bloodfall() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.RARE, CardTarget.ALL_ENEMY);
        this.baseDamage = 0;
        this.isMultiDamage = true;
        this.exhaust = true; // 消耗
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 计算手中所有攻击牌的伤害总和
        int totalDamage = 0;
        for (AbstractCard c : p.hand.group) {
            if (c.type == CardType.ATTACK && c != this) {
                totalDamage += c.damage;
            }
        }
        
        // 计算血瀑的倍数效果
        int bloodfallCount = 0;
        for (AbstractCard c : p.hand.group) {
            if (c.cardID.equals(this.cardID)) {
                bloodfallCount++;
            }
        }
        
        // 根据血瀑数量计算倍数（1倍，2倍，4倍...）
        int multiplier = 1;
        for (int i = 1; i < bloodfallCount; i++) {
            multiplier *= 2;
        }
        
        totalDamage *= multiplier;
        
        // 对所有敌人造成伤害
        if (totalDamage > 0) {
            int[] damageArray = new int[AbstractDungeon.getMonsters().monsters.size()];
            for (int i = 0; i < damageArray.length; i++) {
                damageArray[i] = totalDamage;
            }
            addToBot((AbstractGameAction)new DamageAllEnemiesAction(p, damageArray, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HEAVY));
        }
    }

    @Override
    public void applyPowers() {
        // 计算手中所有攻击牌的伤害总和
        int totalDamage = 0;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.type == CardType.ATTACK && c != this) {
                totalDamage += c.damage;
            }
        }
        
        // 计算血瀑的倍数效果
        int bloodfallCount = 0;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.cardID.equals(this.cardID)) {
                bloodfallCount++;
            }
        }
        
        // 根据血瀑数量计算倍数（1倍，2倍，4倍...）
        int multiplier = 1;
        for (int i = 1; i < bloodfallCount; i++) {
            multiplier *= 2;
        }
        
        totalDamage *= multiplier;
        
        this.baseDamage = totalDamage;
        super.applyPowers();
        
        // 更新描述文本显示伤害
        this.rawDescription = cardStrings.DESCRIPTION;
        if (totalDamage > 0) {
            this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + totalDamage;
        }
        this.initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            // 血瀑升级后不消耗
            this.exhaust = false;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Bloodfall();
    }
}