package EchoForm.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.MasterOfStrategy;
import com.megacrit.cardcrawl.cards.curses.AscendersBane;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class ChessPiece extends CustomRelic {
    public static final String ID = "echoForm:ChessPiece";
    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final String IMG = "echoFormResources/images/relics/ChessPiece.png";

    public ChessPiece() {
        super(ID, new Texture(Gdx.files.internal(IMG)), RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        // 拾起时，将你的进阶之灾变为战略大师
        // 遍历牌组，找到进阶之灾并替换为战略大师
        for (int i = AbstractDungeon.player.masterDeck.group.size() - 1; i >= 0; i--) {
            AbstractCard card = AbstractDungeon.player.masterDeck.group.get(i);
            if (card.cardID.equals("AscendersBane")) {
                // 移除进阶之灾
                AbstractDungeon.player.masterDeck.removeCard(card);
                // 添加战略大师
                AbstractCard masterOfStrategy = new MasterOfStrategy();
                AbstractDungeon.player.masterDeck.addToTop(masterOfStrategy);
            }
        }
    }

    @Override
    public void onMonsterDeath(AbstractMonster m) {
        // 每击败一个精英就在你的牌组中加入一张战略大师
        if (m.type == AbstractMonster.EnemyType.ELITE) {
            this.flash();
            // 获得一张战略大师卡牌
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new MasterOfStrategy(),Settings.WIDTH/2,Settings.HEIGHT/2));
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new ChessPiece();
    }
}
