package EchoForm.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import java.util.ArrayList;

public class Tombstone extends CustomRelic {
    public static final String ID = "echoForm:Tombstone";
    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final String IMG = "echoFormResources/images/relics/Tombstone.png";
    
    private static final int MAX_HP_BONUS = 8;
    private static final int GOLD_BONUS = 90;
    private static final int RELIC_LIMIT = 16;

    public Tombstone() {
        super(ID, new Texture(Gdx.files.internal(IMG)), RelicTier.BOSS, LandingSound.HEAVY);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        // 拾起时，获得8点生命上限，获得90金币，选择强化一张牌
        this.flash();
        AbstractDungeon.player.increaseMaxHp(MAX_HP_BONUS, true);
        addToBot((AbstractGameAction)new GainGoldAction(GOLD_BONUS));
        
        // 选择强化一张牌
        if (!AbstractDungeon.player.masterDeck.getUpgradableCards().group.isEmpty()) {
            // 打开强化界面
            if (!AbstractDungeon.isScreenUp) {
                AbstractDungeon.gridSelectScreen.open(
                    AbstractDungeon.player.masterDeck.getUpgradableCards(),
                    1,
                    DESCRIPTIONS[1],
                    true,
                    false,
                    false,
                    false
                );
            }
        }
    }

    @Override
    public void onVictory() {
        // 战斗结束后额外掉落一个碑块
AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(new SteleFragment()));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Tombstone();
    }
}
