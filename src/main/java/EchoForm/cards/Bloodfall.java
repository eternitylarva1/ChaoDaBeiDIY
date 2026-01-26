package EchoForm.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.unique.WhirlwindAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.Whirlwind;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;

public class Bloodfall extends CustomCard {
    public static final String ID = "Bloodfall";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "echoFormResources/images/cards/Bloodfall_attack.png";
    private static final int COST = -1; // X费

    public Bloodfall() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.RARE, CardTarget.ALL_ENEMY);
        this.baseDamage = 0;
        this.isMultiDamage = true;
        this.exhaust = true; // 消耗
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Bloodfall.this.applyPowers();
        final int totaldamage=this.damage;
        // 计算手中所有攻击牌的伤害总和
        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {


                // 对所有敌人造成伤害
                if (totaldamage> 0) {
                    int[] damageArray = new int[AbstractDungeon.getMonsters().monsters.size()];
                    for (int i = 0; i < damageArray.length; i++) {
                        damageArray[i] = Bloodfall.this.damage;
                    }
                    int effect = EnergyPanel.totalCount;
                    if (Bloodfall.this.energyOnUse != -1) {
                        effect = Bloodfall.this.energyOnUse;
                    }

                    if (AbstractDungeon.player.hasRelic("Chemical X")) {
                        effect += 2;
                        AbstractDungeon.player.getRelic("Chemical X").flash();
                    }

                    if (effect > 0) {
                        for (int i = 0; i < effect; ++i) {
                            if (i == 0) {
                                this.addToBot(new SFXAction("ATTACK_WHIRLWIND"));
                                this.addToBot(new VFXAction(new WhirlwindEffect(), 0.0F));
                            }

                            addToBot((AbstractGameAction) new DamageAllEnemiesAction(p, damageArray, Bloodfall.this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HEAVY));
                        }
                    }
                    if (!Bloodfall.this.freeToPlayOnce) {
                        AbstractDungeon.player.energy.use(EnergyPanel.totalCount);
                    }
                    isDone = true;
                }
            }

            ;
        });}



    @Override
    public void applyPowers() {
        // 计算手中所有攻击牌的伤害总和
        int totalDamage = 0;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.type == CardType.ATTACK && !c.cardID.equals(Bloodfall.this.cardID)) {
                totalDamage += c.damage;
            }
        }
        
        // 计算血瀑的倍数效果

        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.cardID.equals(Bloodfall.this.cardID)) {
                if (AbstractDungeon.player.hand.group.indexOf(c)<AbstractDungeon.player.hand.group.indexOf(Bloodfall.this)) {
                    totalDamage+= c.damage;
                }else if (!AbstractDungeon.player.hand.group.contains(Bloodfall.this)){
                    totalDamage+= c.damage;
                }
            }
        }
        this.baseDamage = totalDamage;
        super.applyPowers();
        
        // 更新描述文本显示伤害
        this.rawDescription = cardStrings.DESCRIPTION;
        if (totalDamage > 0) {
            this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0];
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