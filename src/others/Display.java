package others;

import java.util.ArrayList;
import java.util.Scanner;

import action.Action;
import action.ActionBasicAttack;
import action.ActionBasicGuard;
import action.ActionEquip;
import action.ActionItem;
import action.ActionMagic;
import action.ActionSkill;
import chr.Chr;
import chr.Party;

public class Display {
	private static Scanner sc = new Scanner(System.in);
	private static final int ATTACK = 0;
	private static final int SKILL = 1;
	private static final int MAGIC = 2;
	private static final int ITEM = 3;
	private static final int GUARD = 4;
	private static final int EQUIP = 5;

	public static void printStatus(ArrayList<Chr> member) {
		System.out.println("***********************************");
		System.out.printf("%3s", "");
		for (int i = 0; i < member.size(); i++) {
			System.out.printf("%5s", member.get(i).name);
		}
		System.out.println();
		System.out.printf("%3s", "HP");
		for (int i = 0; i < member.size(); i++) {
			System.out.printf("%8d", member.get(i).HP);
		}
		System.out.println();
		System.out.printf("%3s", "MP");
		for (int i = 0; i < member.size(); i++) {
			System.out.printf("%8d", member.get(i).MP);
		}
		System.out.println();
		System.out.printf("%3s", "ATK");
		for (int i = 0; i < member.size(); i++) {
			System.out.printf("%8d", member.get(i).ATK);
		}
		System.out.println();
		System.out.printf("%3s", "DEF");
		for (int i = 0; i < member.size(); i++) {
			System.out.printf("%8d", member.get(i).DEF);
		}
		//System.out.println();
		//System.out.printf("%3s", "SPD");

		System.out.println();
		System.out.println("***********************************");
	}

	public static Action printAndSelectPCAction(String name, ArrayList<Action> actions) {
		int actionNum = 0;
		String actionName = "";
		Action action = null;
		ArrayList<Action> selAction = null;
		
		ArrayList<Action> attack = new ArrayList<>();
		ArrayList<Action> skill = new ArrayList<>();
		ArrayList<Action> magic = new ArrayList<>();
		ArrayList<Action> item = new ArrayList<>();
		ArrayList<Action> guard = new ArrayList<>();
		ArrayList<Action> equip = new ArrayList<>();
		
		for (int i = 0; i < actions.size(); i++) {
			action = actions.get(i);
			if (action instanceof ActionBasicAttack) {
				attack.add(action);
			} else if (action instanceof ActionSkill) {
				skill.add(action);
			} else if (action instanceof ActionMagic) {
				magic.add(action);
			} else if (action instanceof ActionItem) {
				item.add(action);
			} else if (action instanceof ActionBasicGuard) {
				guard.add(action);
			} else if (action instanceof ActionEquip) {
				equip.add(action);
			}
		}
		
		while (true) {
			System.out.println(name + "の行動の番号を入力");
			System.out.println("0.こうげき");
			System.out.println("1.とくぎ");
			System.out.println("2.まほう");
			System.out.println("3.どうぐ");
			System.out.println("4.ぼうぎょ");
			System.out.println("5.そうび");
			
			actionNum = scanNumber(EQUIP);
			
			if (actionNum == ATTACK) {
				selAction = new ArrayList<>(attack);
				actionName = "こうげき";
			} else if (actionNum == SKILL) {
				selAction = new ArrayList<>(skill);
				actionName = "とくぎ";
			} else if (actionNum == MAGIC) {
				selAction = new ArrayList<>(magic);
				actionName = "まほう";
			} else if (actionNum == ITEM) {
				selAction = new ArrayList<>(item);
				actionName = "どうぐ";
			} else if (actionNum == GUARD) {
				selAction = new ArrayList<>(guard);
				actionName = "ぼうぎょ";
			} else if (actionNum == EQUIP) {
				selAction = new ArrayList<>(equip);
				actionName = "そうび";
			}
			
			if (selAction.size() == 0) {
				if (actionNum == ATTACK) {
					System.out.println("こうげきできない！");
				} else if (actionNum == SKILL) {
					System.out.println("とくぎを覚えていない！");
				} else if (actionNum == MAGIC) {
					System.out.println("まほうを覚えていない！");
				} else if (actionNum == ITEM) {
					System.out.println("どうぐを持っていない！");
				} else if (actionNum == GUARD) {
					System.out.println("ぼうぎょできない！");
				} else if (actionNum == EQUIP) {
					System.out.println("なにもそうびしていない！");
				}
			}else if (actionNum == ATTACK || actionNum == GUARD) {
				action = selAction.get(0);
				break;
			} else if (actionNum != ATTACK){
				System.out.println(name + "の" + actionName + "の番号を入力");
				for (int i = 0; i < selAction.size(); i++) {
					System.out.printf("%d.%s", i, selAction.get(i).name);
					System.out.println();
				}
				
				actionNum = scanNumber(selAction.size() - 1);
				action = selAction.get(actionNum);
				break;
			}
		}
		return action;
		
	}

	public static Chr printAndSelectTargets(ArrayList<Chr> memList) {
		for (int i = 0; i < memList.size(); i++) {
			String name = memList.get(i).name;
			System.out.printf(i + ".%s", name);
			System.out.println();
		}
		int targetNum = scanNumber(memList.size() - 1);
		Chr targetChr = memList.get(targetNum);
		return targetChr;
	}

	public static int scanNumber(int max) {
		String str = "";
		int num = 0;
		while (true) {
			try {
				str = sc.nextLine();
				num = Integer.parseInt(str);
				if (num <= max) {
					break;
				} else {
					System.out.println("0から" + max + "までの整数を入力してください");
					continue;
				}
			} catch (NumberFormatException e) {
				System.out.println("0から" + max + "までの整数を入力してください");
				continue;
			}
		}
		return num;
	}
	
	public static int calcMultiDmg(Chr attacker, Chr defender, int multi) {
		int Dmg = 0;
		Dmg = (int) ((attacker.ATK * multi * (1000 + Math.random() * 200)) / 1000 / 100
				- defender.DEF * defender.DEFMulti / 100);
		if (Dmg < 0) {
			Dmg = 0;
		} else if (Dmg > 9999) {
			Dmg = 9999;
		}
		return Dmg;
	}
	
	public static void judgeHP(Chr attacker, Chr defender) {
		if (defender.HP <= 0) {
			defender.HP = 0;
			if (attacker.party.pKind == Party.PARTY_KIND_ALLY) {
				System.out.println(defender.name + "をたおした！");
			} else {
				System.out.println(defender.name + "はしんでしまった！");
			}
		}
	}
}
