package ea.sample.assignment.notification;

import ea.sample.assignment.domain.Message;

class FileWrite implements ObserverBehaviour {

    private volatile ObserverBehaviour fileWriteObserverBehaviour;

    public ObserverBehaviour getBehaviour() {

        if ( fileWriteObserverBehaviour == null ) {
            synchronized (this) {
                if ( fileWriteObserverBehaviour == null ) {
                    fileWriteObserverBehaviour = new FileWrite();
                }
            }
        }

        return fileWriteObserverBehaviour;
    }

    @Override
    public void doOnUpdate( ObserverUser observer, Message message ) {


    }
}
