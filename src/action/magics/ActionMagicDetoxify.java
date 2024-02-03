package action.magics;

import action.ActionMagic;
import chr.Chr;
import others.IO;

public class ActionMagicDetoxify extends ActionMagic {

	public ActionMagicDetoxify(Chr me) {
		super(me);
		name = "デトックス";
		MPCons = 8;
	}
	
	private int clearStatusNo = me.STATUS_POISONED; 

	@Override
	public boolean playerTarget() {
		return IO.selectSingleTarget(me.party.member, me);
	}

	@Override
	public void execute() {
		IO.msgln("【%sは%sを唱えた！】", me.name, name);

		IO.clearStatus(me, clearStatusNo);

		me.MP -= MPCons;
	}

}
