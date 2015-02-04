package ch.elca.training.services;

import org.springframework.transaction.annotation.Transactional;

/**
 * Based {@link Service} that guarantee all subclasses will do all their actions
 * in a transaction.
 * 
 * @author DTR
 */
@Transactional
public interface Service {

}
