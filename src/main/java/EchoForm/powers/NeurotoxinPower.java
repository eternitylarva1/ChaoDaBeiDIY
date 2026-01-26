package EchoForm.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;

import java.util.ArrayList;

public class NeurotoxinPower extends AbstractPower {
    public static final String POWER_ID = "echoForm:NeurotoxinPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public NeurotoxinPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("poison");
        this.type = AbstractPower.PowerType.DEBUFF;
        this.isTurnBased = true;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    // 当怪物准备攻击时，随机改变目标
    public void onMonsterAttack(AbstractMonster monster, AbstractCreature originalTarget) {
        if (monster.hasPower(PoisonPower.POWER_ID) && monster.hasPower(POWER_ID)) {
            // 获取所有可能的目标
            ArrayList<AbstractCreature> possibleTargets = new ArrayList<>();
            
            // 添加玩家
            possibleTargets.add(AbstractDungeon.player);
            
            // 添加其他怪物（除了自己）
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if (m != monster && !m.isDead && !m.isDying) {
                    possibleTargets.add(m);
                }
            }
            
            // 随机选择一个目标
            if (possibleTargets.size() > 1) {
                AbstractCreature newTarget = possibleTargets.get(AbstractDungeon.cardRandomRng.random(possibleTargets.size() - 1));
                // 这里需要修改怪物的攻击目标，但这需要更复杂的hook或patch
                // 暂时只实现基础的中毒效果
            }
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}