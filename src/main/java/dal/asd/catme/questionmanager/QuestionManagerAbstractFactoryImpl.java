package dal.asd.catme.questionmanager;

public class QuestionManagerAbstractFactoryImpl implements IQuestionManagerAbstractFactory
{
    private static IQuestionManagerAbstractFactory questionManagerAbstractFactory=null;
    private IQuestionDao questionDao;
    private IQuestionManagerService questionManagerService;
    private IListQuestionsService listQuestionsService;

    public QuestionManagerAbstractFactoryImpl()
    {
        questionDao = new QuestionDaoImpl();
        questionManagerService = new QuestionManagerServiceImpl(questionDao);
        listQuestionsService = new ListQuestionsServiceImpl(questionDao);
    }

    public static IQuestionManagerAbstractFactory instance()
    {
        if(questionManagerAbstractFactory == null)
        {
            questionManagerAbstractFactory = new QuestionManagerAbstractFactoryImpl();
        }
        return questionManagerAbstractFactory;
    }

    @Override
    public IQuestionDao getQuestionDao()
    {
        return questionDao;
    }

    @Override
    public IQuestionManagerService getQuestionManagerService()
    {
        return questionManagerService;
    }

    @Override
    public IListQuestionsService getListQuestionsService()
    {
        return listQuestionsService;
    }
}
