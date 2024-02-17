package action.skills;

import action.ActionSkill;
import chr.Chr;
import others.IO;

public class ActionSkillCopOut extends ActionSkill {

	public ActionSkillCopOut(Chr me) {
		super(me);
		name = "うけながし";
	}
	
	public int denominator = 8;
	public int toEnemy = 5;
	public int toAlly = 2;
	
	// 対象：5/8で敵、2/8で味方、1/8で自分自身
	// 疾風突きよりも先に行動
	public boolean playerTarget() {
		me.SPDNext = me.MAX_SPD * 2;
		return true;
	}

	// 効果：物理ダメージを自分以外に受け流す（失敗有り）
	public void execute() {
		IO.msgln("【%sの%s！】", me.name, name);
		IO.msgln("%sは受け流そうと構えている！", me.name);
		
		me.copOut = true;
	}
}
