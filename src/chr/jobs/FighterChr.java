package chr.jobs;

import action.ActionBasicAttack;
import action.ActionBasicGuard;
import action.ActionEquipment;
import action.ActionItem;
import action.skills.ActionSkillBakuretsuken;
import action.skills.ActionSkillMawashigeri;
import action.skills.ActionSkillMeteorStrike;
import action.skills.ActionSkillSeikenzuki;
import chr.Chr;

public class FighterChr extends Chr {
	
	public FighterChr() {
		super(200, 30, 50, 30, 25, 20, 25);
		
		jobName = "格闘家";
		
		// ベースアクション
		actions.add(new ActionBasicAttack(this));
		actions.add(new ActionItem(this));
		actions.add(new ActionEquipment(this));
		
		// スキル
		actions.add(new ActionBasicGuard(this));
		actions.add(new ActionSkillMawashigeri(this));
		actions.add(new ActionSkillSeikenzuki(this));
		actions.add(new ActionSkillMeteorStrike(this));
		actions.add(new ActionSkillBakuretsuken(this));
		
		// 魔法なし
		
		setToNPC();
	}
	
	public void setToNPC() {
	}
	
	public boolean nonPlayerCommand() {
		return true;
	}
}
