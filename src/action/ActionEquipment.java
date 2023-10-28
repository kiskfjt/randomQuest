package action;

import chr.Chr;

public class ActionEquipment extends Action {

	public ActionEquipment(Chr me) {
		super(me);
		name = "そうび";
		rangeMin = me.equipment.rangeMin;
		rangeMax = me.equipment.rangeMax;
		multi = me.equipment.multi;
	}
	/**
	 * 装備クラスのターゲット選択メソッドを実行する
	 */
	public boolean playerTarget() {
		return me.equipment.playerTarget();
	}
	
	/**
	 * 装備クラスのexecuteメソッドを実行する
	 */
	public void execute() {
		me.equipment.playerTarget();
	}

}
