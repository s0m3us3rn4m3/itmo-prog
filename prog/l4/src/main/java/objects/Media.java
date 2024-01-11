package objects;

import enums.MediaEnum;
import exceptions.ObjectException;

import java.util.*;

public abstract class Media {    
    private MediaEnum type;
    private String name;
    private List<Rate> rates;

    public Media(String name, MediaEnum type) {
        this.type = type;
        this.name = name;
    }

    private class Rate {
        private int grade;
        private String msg;

        Rate(int grade, String msg) {
            this.grade = grade;
            this.msg = msg;
        }

        public int getGrade() {
            return grade;
        }

        public String getMsg() {
            return msg;
        }
    }
    
    private void checkGrade(int grade) {
        class InvalidGrade extends RuntimeException {
            InvalidGrade() {
                super("Оценка должна быть числом от 1 до 5");
            }
        }
        if (!(1 <= grade && grade <= 5)) {
            throw new InvalidGrade();
        }
    }
    
    public void sendFeedback(int grade) {
        checkGrade(grade);
        rates.add(new Rate(grade, ""));
    }

    public void sendFeedback(int grade, String msg) {
        checkGrade(grade);
        rates.add(new Rate(grade, msg));
    }

    public MediaEnum getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    abstract public void publish(String news);

    @Override
    public String toString() {
        return String.format("%s \"%s\"", this.type, this.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.type, this.name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        return this.name.equals(((Media)obj).getName()) && this.type.equals(((Media)obj).getType());
    }
}
