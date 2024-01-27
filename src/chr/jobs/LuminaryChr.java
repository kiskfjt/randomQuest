package chr.jobs;

import action.ActionBasicAttack;
import action.ActionBasicGuard;
import action.ActionEquipment;
import action.ActionItem;
import action.magics.ActionMagicInvincible;
import action.magics.ActionMagicLightning;
import action.magics.ActionMagicSongOfNymph;
import action.skills.ActionSkillDazzleFlash;
import action.skills.ActionSkillHustleDance;
import action.skills.ActionSkillKerplunkDance;
import action.skills.ActionSkillMoonSault;
import action.skills.ActionSkillSpookyAura;
import chr.Chr;

public class LuminaryChr extends Chr {
	
	public LuminaryChr() {
		super(200, 100, 45, 30, 40, 30, 30);
		
		jobName = "スーパースター";
		
		actions.add(new ActionBasicAttack(this));
		actions.add(new ActionBasicGuard(this));
		actions.add(new ActionItem(this));
		actions.add(new ActionEquipment(this));
		
		actions.add(new ActionSkillDazzleFlash(this));
		actions.add(new ActionMagicInvincible(this));
		actions.add(new ActionMagicLightning(this));
		actions.add(new ActionSkillKerplunkDance(this));
		actions.add(new ActionSkillSpookyAura(this));
		actions.add(new ActionSkillHustleDance(this));
		actions.add(new ActionSkillMoonSault(this));
		actions.add(new ActionMagicSongOfNymph(this));
	}
	
	public boolean nonPlayerCommand() {
		return true;
	}

}
