package action;

import chr.Chr;

public class ActionEquipment extends Action {

	public ActionEquipment(Chr me) {
		super(me);
		name = "そうび";
		rangeMin = me.equipment == null ? Action.RANGE_MIN_DEFAULT_VALUE : me.equipment.rangeMin;
		rangeMax = me.equipment == null ? Action.RANGE_MAX_DEFAULT_VALUE : me.equipment.rangeMax;
		multi = me.equipment == null ? Action.MULTI_DEFAULT_VALUE : me.equipment.multi;
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
		me.equipment.execute();
	}

}
