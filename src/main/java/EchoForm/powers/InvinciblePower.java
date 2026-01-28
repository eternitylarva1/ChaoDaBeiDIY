package EchoForm.powers;

import EchoForm.utils.TextureUtils;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class InvinciblePower extends AbstractPower {
    public static final String POWER_ID = "Invincible1";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    
    private static final String IMG_PATH_128 = "echoFormResources/images/powers/InviciblePower84.png";
    private static final String IMG_PATH_48 = "echoFormResources/images/powers/InviciblePower32.png";

    private static TextureAtlas.AtlasRegion region128;
    private static TextureAtlas.AtlasRegion region48;

    static {
        Texture tex = ImageMaster.loadImage(IMG_PATH_128);
        if (tex != null) {
            region128 = new TextureAtlas.AtlasRegion(tex, 0, 0, 84, 84);
            region48 = TextureUtils.resizeTexture(region128, 48, 48);
        }
    }

    public InvinciblePower(AbstractCreature owner, int turns) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = turns;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        updateDescription();
        this.img=ImageMaster.loadImage(IMG_PATH_48);

        this.priority = 75;
    }

    @Override
    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_INTANGIBLE", 0.05F);
    }

    @Override
    public float atDamageFinalReceive(float damage, DamageInfo.DamageType type) {
        // 真正的无敌：将所有伤害降低到0
        return 0.0F;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

}
