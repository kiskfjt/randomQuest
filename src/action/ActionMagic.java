package action;

import chr.Chr;

public abstract class ActionMagic extends Action {

	public ActionMagic(Chr me) {
		super(me);
		name = "まほう";
	}
	
	// 消費MP
	public int MPCons;

}
