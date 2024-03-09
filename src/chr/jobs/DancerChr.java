package chr.jobs;

import action.ActionBasicAttack;
import action.ActionBasicGuard;
import action.ActionEquipment;
import action.ActionItem;
import action.skills.ActionSkillBanDance;
import action.skills.ActionSkillCopOut;
import action.skills.ActionSkillDeathDance;
import action.skills.ActionSkillDodgeDance;
import action.skills.ActionSkillFuddleDance;
import action.skills.ActionSkillMysteryWaltz;
import action.skills.ActionSkillPassionateTango;
import action.skills.ActionSkillWeirdDance;
import chr.Chr;

public class DancerChr extends Chr {
	
	public DancerChr() {
		super(150, 50, 30, 20, 30, 20, 25);
		
		jobName = "踊り子";
		
		actions.add(new ActionBasicAttack(this));
		actions.add(new ActionBasicGuard(this));
		actions.add(new ActionItem(this));
		actions.add(new ActionEquipment(this));
		actions.add(new ActionSkillDodgeDance(this));
		actions.add(new ActionSkillCopOut(this));
		actions.add(new ActionSkillWeirdDance(this));
		actions.add(new ActionSkillMysteryWaltz(this));
		actions.add(new ActionSkillPassionateTango(this));
		actions.add(new ActionSkillFuddleDance(this));
		actions.add(new ActionSkillBanDance(this));
		actions.add(new ActionSkillDeathDance(this));
	}
	
	public boolean nonPlayerCommand() {
		// TODO 自動生成されたメソッド・スタブ
		return true;
	}

}
