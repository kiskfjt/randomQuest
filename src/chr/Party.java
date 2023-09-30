package chr;

import java.util.ArrayList;

import chr.jobs.BraveChr;
import chr.jobs.PriestChr;
import others.IO;

public class Party {
	public static final int PARTY_KIND_ALLY = 0;
	public static final int PARTY_KIND_ENEMY = 1;
	public ArrayList<Chr> member;
	public Party enemy;
	public int pKind;
	
	public Party(String[] name, int partyKind) {
		member = new ArrayList<>();
		for (int i = 0; i < name.length; i++) {

			//todo ランダム選択にする
			Chr chr = null;
			int jobNo = IO.randomNum(1);
			switch (jobNo) {
			case 0:
				chr = new BraveChr(name[i]);
				break;
			case 1:
				chr = new PriestChr(name[i]);
				break;
			}
			
			if (partyKind == PARTY_KIND_ALLY) {
				chr.setToPC();
				pKind = PARTY_KIND_ALLY;
			} else {
				pKind = PARTY_KIND_ENEMY;
			}
			
			member.add(chr);
			chr.party = this;
			//System.out.println(member.get(i).name);
			//System.out.println(battle.party.member.get(i).ATK);
		}
	}
	
	public boolean isZenmetsu() {
		for (Chr c : member) {
			if (c.isAlive()) {
				return false;
			}
		}
		return true;
	}
}
