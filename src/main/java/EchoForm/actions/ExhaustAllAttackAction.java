package EchoForm.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

import java.util.ArrayList;

public class ExhaustAllAttackAction extends AbstractGameAction {
    private int platedArmorPerCard;
    
    public ExhaustAllAttackAction(int platedArmorAmount) {
        this.platedArmorPerCard = platedArmorAmount;
        setValues(AbstractDungeon.player, AbstractDungeon.player);
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        ArrayList<AbstractCard> cardsToExhaust = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.type == AbstractCard.CardType.ATTACK) {
                cardsToExhaust.add(c);
            }
        }

        // 计算总的多重护甲层数
        int totalPlatedArmor = cardsToExhaust.size() * this.platedArmorPerCard;
        
        // 如果有攻击牌被消耗，则给予相应层数的多重护甲
        if (totalPlatedArmor > 0) {
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, 
                new PlatedArmorPower(AbstractDungeon.player, totalPlatedArmor), totalPlatedArmor));
        }
        
        // 消耗所有攻击牌
        for (AbstractCard c : cardsToExhaust) {
            addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
        }
        
        this.isDone = true;
    }
}