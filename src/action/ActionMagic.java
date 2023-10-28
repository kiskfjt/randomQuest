package action;

import chr.Chr;

public class ActionMagic extends Action {

	public ActionMagic(Chr me) {
		super(me);
		name = "まほう";
	}
	
	// 消費MP
	public int MPCons;

	public boolean playerTarget() {
		return false;
		// TODO 自動生成されたメソッド・スタブ
		
	}

	public void execute() {
		// TODO 自動生成されたメソッド・スタブ
		
	}

}
