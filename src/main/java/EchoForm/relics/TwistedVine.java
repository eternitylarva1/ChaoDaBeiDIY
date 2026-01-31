package EchoForm.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.EventRoom;

import static EchoForm.patchs.TwistedVinePatch.TwistVineOn;

public class TwistedVine extends CustomRelic {
    public static final String ID = "echoForm:TwistedVine";
    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final String IMG = "echoFormResources/images/relics/TwistedVine.png";
    
    private static final float HP_REDUCTION = 0.25f;

    public TwistedVine() {
        super(ID, new Texture(Gdx.files.internal(IMG)), RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {

    }

    @Override
    public void atBattleStart() {
   if (!TwistVineOn){
       return;
   }
        // 参考昆虫标本遗物：降低怪物最大生命25%
        this.flash();
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (m.currentHealth > (int)(m.maxHealth * (1.0F - HP_REDUCTION))) {
                m.currentHealth = (int)(m.maxHealth * (1.0F - HP_REDUCTION));
                m.healthBarUpdatedEvent();
            }
        }
        addToTop((AbstractGameAction)new RelicAboveCreatureAction((AbstractCreature)AbstractDungeon.player, this));
    }

    @Override
    public void onVictory() {
        if (!TwistVineOn){
            return;
        }
        // 在战斗结束后额外掉落一个遗物
        if (AbstractDungeon.getCurrRoom() != null &&
            AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            // 添加一个随机遗物到奖励中
            AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.COMMON));
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new TwistedVine();
    }
}
