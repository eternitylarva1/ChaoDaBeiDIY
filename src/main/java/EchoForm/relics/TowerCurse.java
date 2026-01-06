package EchoForm.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class TowerCurse extends CustomRelic {
    public static final String ID = "echoForm:TowerCurse";
    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final String IMG = "echoFormResources/images/relics/TowerCurse.png";

    public TowerCurse() {
        super(ID, new Texture(Gdx.files.internal(IMG)), RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onLoseHp(int damageAmount) {
        // 每当你失去生命时，在你的抽牌堆中加入一张炸弹，其首次打出的耗能为0
        if (damageAmount > 0) {
            this.flash();
            // 创建一张燃烧牌（作为炸弹的替代）
            AbstractCard bomb = new Burn();
            // 设置首次打出耗能为0
            bomb.costForTurn = 0;
            bomb.isCostModifiedForTurn = true;
            // 加入抽牌堆
            addToBot((AbstractGameAction)new MakeTempCardInDrawPileAction(bomb, 1, true, false));
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new TowerCurse();
    }
}