package EchoForm.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.ThreadAndNeedle;

public class AngryFlower extends CustomRelic {
    public static final String ID = "echoForm:AngryFlower";
    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final String IMG = "echoFormResources/images/relics/AngryFlower.png";
    
    private static final int MAX_ARMOR = 15;
    private static final float HP_PERCENTAGE = 0.1f;

    public AngryFlower() {
        super(ID, new Texture(Gdx.files.internal(IMG)), RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        // 战斗开始时获得相当于敌人最大生命10%的多层护甲，最多15层
        this.flash();
        
        // 找到生命上限最大的敌人
        AbstractMonster target = null;
        int maxHp = 0;
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!m.isDeadOrEscaped() && m.maxHealth > maxHp) {
                maxHp = m.maxHealth;
                target = m;
            }
        }
        
        if (target != null) {
            int armorAmount = (int)(target.maxHealth * HP_PERCENTAGE);
            if (armorAmount > MAX_ARMOR) {
                armorAmount = MAX_ARMOR;
            }
            //todo 待测试
            if (armorAmount > 0) {
                this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new PlatedArmorPower(AbstractDungeon.player, armorAmount), armorAmount));
 }
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new AngryFlower();
    }
}
