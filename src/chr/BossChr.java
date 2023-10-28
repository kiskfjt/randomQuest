package chr;

import action.ActionBasicAttack;
import action.ActionBasicGuard;
import action.skills.ActionSkillMawashigeri;

public class BossChr extends Chr {

	@Override
	public boolean nonPlayerCommand() {
		return false;
	}
	
	public BossChr() {
		super("", 500, 100, 50, 30, 50, 30, 25);
		
		jobName = "ボス";
		
		actions.add(new ActionBasicAttack(this));
		actions.add(new ActionBasicGuard(this));
		actions.add(new ActionSkillMawashigeri(this));
		
		setToNPC();
	}

	private void setToNPC() {
		// TODO 自動生成されたメソッド・スタブ
		
	}
}
