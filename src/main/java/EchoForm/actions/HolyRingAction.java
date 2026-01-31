package EchoForm.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class HolyRingAction extends AbstractGameAction {
    private AbstractPlayer p;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("HolyRingAction");
    public static final String[] TEXT = uiStrings.TEXT;
    private int energyGained = 0;
    private int maxEnergy;
    
    public HolyRingAction(AbstractPlayer p, int maxEnergy) {
        this.p = p;
        this.maxEnergy = maxEnergy;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration=1.0F;
    }

    public void update() {
        if (this.duration == 0.5F) {
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], 99, true, true);
            addToBot(new WaitAction(0.25F));
            tickDuration();
            return;
        }
        
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            if (!AbstractDungeon.handCardSelectScreen.selectedCards.group.isEmpty()) {
                int cardsToDiscard = Math.min(AbstractDungeon.handCardSelectScreen.selectedCards.group.size(), 
                                              this.maxEnergy - this.energyGained);
                
                for (int i = 0; i < cardsToDiscard; i++) {
                    AbstractCard c = AbstractDungeon.handCardSelectScreen.selectedCards.group.get(i);
                    AbstractDungeon.player.hand.moveToDiscardPile(c);
                    c.triggerOnManualDiscard();
                    this.energyGained++;
                }
                
                if (this.energyGained > 0) {
                    addToTop(new GainEnergyAction(this.energyGained));
                }
            }
            
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        
        tickDuration();
    }
}