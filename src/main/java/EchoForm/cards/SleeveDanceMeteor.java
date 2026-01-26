package EchoForm.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.Collections;

public class SleeveDanceMeteor extends CustomCard {
    public static final String ID = "SleeveDanceMeteor";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "echoFormResources/images/cards/SleeveDanceMeteor_skill.png";
    private static final int COST = 3;

    public SleeveDanceMeteor() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardColor.PURPLE, CardRarity.RARE, CardTarget.SELF);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 创建手牌副本
        ArrayList<AbstractCard> handCards = new ArrayList<>();
        for (AbstractCard c : p.hand.group) {
            handCards.add(c);
        }
        
        // 随机打乱顺序
        Collections.shuffle(handCards, AbstractDungeon.cardRandomRng.random);
        
        // 依次处理每张牌
        for (AbstractCard card : handCards) {
            if (p.hand.contains(card)) {
                int action = AbstractDungeon.cardRandomRng.random(5);
                
                switch (action) {
                    case 0: // 打出
                        AbstractMonster target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
                        addToBot((AbstractGameAction)new NewQueueCardAction(card, target, false, true));
                        break;
                    case 1: // 丢弃
                        addToBot((AbstractGameAction)new DiscardAction(p, p, 1, false));
                        break;
                    case 2: // 复制
                        AbstractCard copy = card.makeSameInstanceOf();
                        addToBot((AbstractGameAction)new MakeTempCardInHandAction(copy, 1));
                        break;
                    case 3: // 升级
                        if (!card.upgraded) {
                            card.upgrade();
                        }
                        break;
                    case 4: // 变化费用
                            int newCost = AbstractDungeon.cardRandomRng.random(3);
                            if (newCost != card.costForTurn) {
                                card.setCostForTurn(newCost);
                            }
                        break;
                    case 5: // 消耗（只消耗非攻击牌）
                        if (card.type != AbstractCard.CardType.ATTACK) {
                            addToBot((AbstractGameAction)new ExhaustSpecificCardAction(card, p.hand));
                        }
                        break;
                }
            }
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(2);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new SleeveDanceMeteor();
    }
}