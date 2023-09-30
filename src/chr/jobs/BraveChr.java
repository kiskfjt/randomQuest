package chr.jobs;

import java.util.ArrayList;

import action.ActionBasicAttack;
import action.ActionBasicGuard;
import action.skills.ActionSkillMawashigeri;
import chr.Chr;
import others.IO;

public class BraveChr extends Chr {
	private final int A_ATTACK = 0;
	private final int A_GUARD = 1;
	private final int A_MAWASHIGERI = 2;
	public BraveChr(String name) {
		super(name, 500, 100, 50, 30, 50, 30, 25);
		
		jobName = "ゆうしゃ";
		
		actions.add(new ActionBasicAttack(this));
		actions.add(new ActionBasicGuard(this));
		actions.add(new ActionSkillMawashigeri(this));
		
		setToNPC();
	}
	
	public void setToNPC() {
		command = this::nonPlayerCommand;
		actions.get(A_ATTACK).target = this::attackTarget;
		actions.get(A_GUARD).target = this::guardTarget;
		actions.get(A_MAWASHIGERI).target = this::mawashigeriTarget;
	}
	
	/**
	 * NPC「ゆうしゃ」の行動選択メソッド
	 * 「こうげき」、「ぼうぎょ」、「まわしげり」からランダムに選択
	 */
	public void nonPlayerCommand() {
		int actionNum = IO.randomNum(A_MAWASHIGERI);
		if (actionNum == A_ATTACK) {
			action = actions.get(A_ATTACK);
			action.target = this::attackTarget;
		} else if (actionNum == A_GUARD) {
			action = actions.get(A_GUARD);
			action.target = this::guardTarget;
		} else if (actionNum == A_MAWASHIGERI) {
			action = actions.get(A_MAWASHIGERI);
			action.target = this::mawashigeriTarget;
		}
	}
	
	/**
	 * NPC「ゆうしゃ」の「こうげき」のターゲット決定メソッド
	 * 敵パーティーからランダムに1体選択
	 */
	public void attackTarget() {
		ArrayList<Chr> list = new ArrayList<>(party.enemy.member);
	    int targetNum = 0;
	    Chr target = null;
	    while (true) {
	    	targetNum = (int) (Math.random() * list.size());
	    	target = list.get(targetNum);
	    	if (target.isAlive()) {
	    		this.targets.add(target);
	    		break;
	    	}
	    }
	}
	
	public void guardTarget() {
		targets.clear();
	}
	
	public void mawashigeriTarget() {
		ArrayList<Chr> list = new ArrayList<>(party.enemy.member);
		for (Chr c : list) {
			if (c.isAlive()) {
				targets.add(c);
			}
		}
	}
}
