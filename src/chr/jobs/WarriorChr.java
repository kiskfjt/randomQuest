package chr.jobs;

import action.ActionBasicAttack;
import action.ActionBasicGuard;
import action.ActionEquipment;
import action.ActionItem;
import action.skills.ActionSkillHayabusagiri;
import action.skills.ActionSkillKiaitame;
import action.skills.ActionSkillMajingiri;
import action.skills.ActionSkillMinagoroshi;
import action.skills.ActionSkillMorobagiri;
import action.skills.ActionSkillShippu;
import chr.Chr;

public class WarriorChr extends Chr {
	
	public WarriorChr(String name) {
		super(name, 160, 50, 50, 30, 25, 20, 20);
		
		jobName = "せんし";
		
		actions.add(new ActionBasicAttack(this));
		actions.add(new ActionBasicGuard(this));
		actions.add(new ActionItem(this));
		actions.add(new ActionEquipment(this));
		actions.add(new ActionSkillKiaitame(this));
		actions.add(new ActionSkillMorobagiri(this));
		actions.add(new ActionSkillMinagoroshi(this));
		actions.add(new ActionSkillShippu(this));
		actions.add(new ActionSkillHayabusagiri(this));
		actions.add(new ActionSkillMajingiri(this));
	}
	
	@Override
	public boolean nonPlayerCommand() {
		// TODO 自動生成されたメソッド・スタブ
		return true;
	}

}
