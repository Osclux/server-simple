package se.konferensplattan;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class GsonInheritanceTest {
    public static void main(String[] args) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        C c = new C();
        c.classes = new ArrayList<A>();
        c.classes.add(new A(1));
        c.classes.add(new B(2, 3));
        System.out.println(gson.toJson(c));
    }

    static class A {
        int stars;

        A(int stars) {
            this.stars = stars;
        }
    }

    static class B extends A {
        int sunshines;

        B(int stars, int sunshines) {
            super(stars);
            this.sunshines = sunshines;
        }
    }

    static class C {
        List<A> classes;
    }
}