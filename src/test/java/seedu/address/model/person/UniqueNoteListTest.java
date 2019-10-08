package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_BOB;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.exceptions.DuplicateNoteException;
import seedu.address.model.person.exceptions.NoteNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class UniqueNoteListTest {

    private final UniqueNoteList uniqueNoteList = new UniqueNoteList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueNoteList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueNoteList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueNoteList.add(ALICE);
        assertTrue(uniqueNoteList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueNoteList.add(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withContent(VALID_CONTENT_BOB).build();
        assertTrue(uniqueNoteList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueNoteList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueNoteList.add(ALICE);
        assertThrows(DuplicateNoteException.class, () -> uniqueNoteList.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueNoteList.setPerson(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueNoteList.setPerson(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(NoteNotFoundException.class, () -> uniqueNoteList.setPerson(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueNoteList.add(ALICE);
        uniqueNoteList.setPerson(ALICE, ALICE);
        UniqueNoteList expectedUniqueNoteList = new UniqueNoteList();
        expectedUniqueNoteList.add(ALICE);
        assertEquals(expectedUniqueNoteList, uniqueNoteList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueNoteList.add(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withContent(VALID_CONTENT_BOB).build();
        uniqueNoteList.setPerson(ALICE, editedAlice);
        UniqueNoteList expectedUniqueNoteList = new UniqueNoteList();
        expectedUniqueNoteList.add(editedAlice);
        assertEquals(expectedUniqueNoteList, uniqueNoteList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueNoteList.add(ALICE);
        uniqueNoteList.setPerson(ALICE, BOB);
        UniqueNoteList expectedUniqueNoteList = new UniqueNoteList();
        expectedUniqueNoteList.add(BOB);
        assertEquals(expectedUniqueNoteList, uniqueNoteList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueNoteList.add(ALICE);
        uniqueNoteList.add(BOB);
        assertThrows(DuplicateNoteException.class, () -> uniqueNoteList.setPerson(ALICE, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueNoteList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(NoteNotFoundException.class, () -> uniqueNoteList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueNoteList.add(ALICE);
        uniqueNoteList.remove(ALICE);
        UniqueNoteList expectedUniqueNoteList = new UniqueNoteList();
        assertEquals(expectedUniqueNoteList, uniqueNoteList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueNoteList.setPersons((UniqueNoteList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueNoteList.add(ALICE);
        UniqueNoteList expectedUniqueNoteList = new UniqueNoteList();
        expectedUniqueNoteList.add(BOB);
        uniqueNoteList.setPersons(expectedUniqueNoteList);
        assertEquals(expectedUniqueNoteList, uniqueNoteList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueNoteList.setPersons((List<Person>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueNoteList.add(ALICE);
        List<Person> personList = Collections.singletonList(BOB);
        uniqueNoteList.setPersons(personList);
        UniqueNoteList expectedUniqueNoteList = new UniqueNoteList();
        expectedUniqueNoteList.add(BOB);
        assertEquals(expectedUniqueNoteList, uniqueNoteList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Person> listWithDuplicatePersons = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateNoteException.class, () -> uniqueNoteList.setPersons(listWithDuplicatePersons));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueNoteList.asUnmodifiableObservableList().remove(0));
    }
}