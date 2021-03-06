package heero.mc.mod.wakcraft.quest;

public class Quest {
    public Integer id;
    public String name;
    public String by;
    public QuestCondition[] conditions;
    public QuestTask[] tasks;

    public QuestTask getTask(final Integer taskId) {
        if (taskId < 0 || taskId >= tasks.length) {
            return null;
        }

        return tasks[taskId];
    }
}
