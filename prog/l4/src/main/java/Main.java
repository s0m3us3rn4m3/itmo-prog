package main.java;

import objects.Media;
import objects.Human;
import objects.MediaGenerator;
import objects.PeopleGroup;
import objects.Subject;
import enums.AbilityEnum;
import enums.MediaEnum;
import exceptions.ObjectException;

/*
 * Каждый момент этого четырехчасового полета навсегда врезался в мою память: он изменил всю мою жизнь. 
 * Именно тогда, в 54-летнем возрасте, я навсегда утратил мир и покой, присущий человеку с нормальным 
 * рассудком и живущему в согласии с природой и ее законами. 
 * 
 * С этого времени мы -- все десятеро, но особенно мы с Денфортом -- неотрывно следили за фантомами, 
 * таящимися в глубинах этого чудовищного искаженного мира, 
 * и ничто не заставит нас позабыть его. Мы не стали бы рассказывать, будь это возможно, о наших переживаниях 
 * всему человечеству. Газеты напечатали бюллетени, посланные нами с борта самолета, в которых сообщалось о 
 * нашем беспосадочном перелете; о встрече в верхних слоях атмосферы с предательскими порывами ветра; 
 * об увиденной с высоты шахте, которую Лейк пробурил три дня назад на полпути к горам, а также о загадочных 
 * снежных цилиндрах, замеченных ранее Амундсеном и Бэрдом,-- ветер гнал их по бескрайней ледяной равнине. 
 * Затем наступил момент, когда мы не могли адекватно передавать охватившие нас чувства, а потом пришел и такой, 
 * когда мы стали строго контролировать свои слова, введя своего рода цензуру. 
 * 
 * Первым завидел впереди зубчатую линию таинственных кратеров и вершин матрос Ларсен. Он так завопил, что все 
 * бросились к иллюминаторам. 
 * Несмотря на значительную скорость самолета, горы, казалось, совсем не приближались; это говорило о том, 
 * что они бесконечно далеки и видны только из-за своей невероятной, непостижимой высоты. И, все же постепенно 
 * они мрачно вырастали перед нами, застилая западную часть неба, и мы уже могли рассмотреть голые, лишенные 
 * растительности и незащищенные от ветра темные вершины. Нас пронизывало непередаваемое ощущение чуда, 
 * переживаемое при виде этих залитых розоватым антарктическим светом громад на фоне облаков ледяной пыли, 
 * переливающейся всеми цветами радуги.
 */

public class Main {
    
    public static void main(String[] args) {
        try {
            Human me = new Human("Я", 54);
            Human denfort = new Human("Денфорт", 50);
            Human larsen = new Human("Ларсен", 50);
            Human[] group1 = {me, denfort};
            PeopleGroup group = new PeopleGroup(group1);
            Media newspaper = MediaGenerator.Generate("газета", MediaEnum.NEWSPAPER);
            Subject mountains = new Subject("Горы");
            
            me.remember("этот четырехчасовой полет", AbilityEnum.DO);
            me.loose("мир и покой, присущий человеку с нормальным рассудком и живущему в согласии с природой и ее законами", AbilityEnum.DO);
    
            group.watch("за фантомами, таящимися в глубинах этого чудовищного искаженного мира", true);
    
            me.tell("о наших переживаниях всему человечеству", AbilityEnum.WOULDNT);
    
            newspaper.publish("о нашем беспосадочном перелете; о встрече в верхних слоях атмосферы с предательскими порывами ветра; об увиденной с высоты шахте, которую Лейк пробурил три дня назад на полпути к горам, а также о загадочных снежных цилиндрах, замеченных ранее Амундсеном и Бэрдом,-- ветер гнал их по бескрайней ледяной равнине.");
    
            me.tell("охватившие нас чувства", AbilityEnum.CANNOT);
            me.tell("строго контролируя свои слова, введя своего рода цензуру", AbilityEnum.CAN);
    
            larsen.see("зубчатую линию таинственных кратеров и вершин", AbilityEnum.DO);
            larsen.shout("", AbilityEnum.DO);
            group.run("к иллюминаторам", true);
    
            mountains.approach("к нам", AbilityEnum.DONT);
            mountains.approach("всё же постепенно к нам, застилая западную часть неба", AbilityEnum.DO);
    
            me.see("голые, лишенные растительности и незащищенные от ветра темные вершины.", AbilityEnum.CAN);
            me.feel("непередаваемое ощущение чуда, переживаемое при виде этих залитых розоватым антарктическим светом громад на фоне облаков ледяной пыли, переливающейся всеми цветами радуги.", AbilityEnum.DO);
        } catch (ObjectException e) {
            System.out.println(e);
        }
    }
}