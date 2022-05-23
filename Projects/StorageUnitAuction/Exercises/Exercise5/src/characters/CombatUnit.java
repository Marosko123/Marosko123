package characters;

import java.util.*;

// The Composite pattern - the Component role

public interface CombatUnit {
	List<? extends CombatUnit> getWarriors();
	String report(String tab);
	
}
