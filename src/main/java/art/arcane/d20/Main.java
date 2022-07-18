package art.arcane.d20;

import art.arcane.d20.roll.Die;
import art.arcane.d20.roll.Roll;
import art.arcane.d20.roll.RollChain;
import art.arcane.d20.roll.SimpleRoll;

public class Main {
    public static void main(String[] a)
    {
        Roll r = Roll
                .d20().modifier(4)
                .build();

        System.out.println(r.encode() + " ");

        for(int i = 0; i < 32; i++)
        {
            System.out.print(r.roll() + " ");
        }
    }
}
