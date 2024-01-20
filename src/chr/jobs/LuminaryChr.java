package chr.jobs;

import action.ActionBasicAttack;
import action.ActionBasicGuard;
import action.ActionEquipment;
import action.ActionItem;
import action.magics.ActionMagicInvincible;
import action.skills.ActionSkillDazzleFlash;
import action.skills.ActionSkillHustleDance;
import chr.Chr;

public class LuminaryChr extends Chr {
	
	public LuminaryChr(String name) {
		super(name, 200, 100, 45, 30, 40, 30, 30);
		
		jobName = "スーパースター";
		
		actions.add(new ActionBasicAttack(this));
		actions.add(new ActionBasicGuard(this));
		actions.add(new ActionItem(this));
		actions.add(new ActionEquipment(this));
		
		actions.add(new ActionSkillDazzleFlash(this));
		actions.add(new ActionMagicInvincible(this));
		actions.add(new ActionSkillHustleDance(this));
	}
	
	@Override
	public boolean nonPlayerCommand() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

}
