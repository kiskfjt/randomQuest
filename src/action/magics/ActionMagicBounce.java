package action.magics;

import action.ActionMagic;
import chr.Chr;
import others.Calc;
import others.IO;

public class ActionMagicBounce extends ActionMagic {

	public ActionMagicBounce(Chr me) {
		super(me);
		name = "マジックミラー";
		MPCons = 15;
		rangeMinInt = 2;
		rangeMaxInt = 4;
		statusNo = me.STATUS_MAGIC_BOUNCE;	}

	// 対象：味方単体
	public boolean playerTarget() {
		return IO.selectSingleTarget(me.party.member, me);
	}

	// 効果：敵の魔法反射、2～4ターン継続
	public void execute() {
		IO.msgln("【%sは%sを唱えた！】", me.name, name);
		
		IO.changeSingleStatus(me, statusNo);
		
		if (me.targets.get(0).isAlive()) {
			IO.msgln("%sの前に輝く光の鏡を作り出した！", me.targets.get(0).name);
		}
		
		Calc.subtractMPConsIfTargetIsAlive(me, MPCons);
	}
}
