package depatm.atmdep;

abstract class AtmProcessor {
    private AtmProcessor next;

    private AtmProcessor getNext() {
        return next;
    }

    void setNext(AtmProcessor next) {
        this.next = next;
    }

    void process(Application application) throws Exception {
        before();
        processInternal(application);
        after();
        if (getNext() != null) {
            getNext().process(application);
        }
    }

    protected abstract void processInternal(Application application) throws Exception;

    public abstract String getProcessorName();

    private void before() {
        System.out.println("before:" + getProcessorName());
    }

    private void after() {
        System.out.println("after:" + getProcessorName());
    }

}