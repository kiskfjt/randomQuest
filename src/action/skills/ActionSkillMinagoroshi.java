package action.skills;

import java.util.ArrayList;

import action.ActionSkill;
import chr.Chr;
import others.Calc;
import others.IO;

public class ActionSkillMinagoroshi extends ActionSkill {

	public ActionSkillMinagoroshi(Chr me) {
		super(me);
		name = "みなごろし";
		multi = 60;
		criticalRate = 100;
	}
	
	// 攻撃範囲：敵味方単体
	public boolean playerTarget() {
		ArrayList<Chr> allList = new ArrayList<>();
		allList.addAll(me.party.member);
		allList.addAll(me.party.enemy.member);
		return IO.selectSingleRandomTarget(allList, me);
	}
	
	// ダメージ：物理、掛け算方式、必ず会心
	public void execute() {
		IO.msgln("【%sの%s！】", me.name, name);
		IO.msgln("%sは敵味方見境なく襲い掛かった！", me.name);
		
		Calc.physSingleDmg(me);
	}

}
