package seedu.programmer.model.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.programmer.testutil.StudentBuilder;

class StudentDetailContainsQueryPredicateTest {
    @Test
    void equals() {
        QueryStudentDescriptor firstDescriptor = new QueryStudentDescriptor();
        QueryStudentDescriptor secondDescriptor = new QueryStudentDescriptor();
        firstDescriptor.setName("One");
        firstDescriptor.setStudentId("A1234567X");
        firstDescriptor.setClassId("B01");
        secondDescriptor.setName("Two");
        secondDescriptor.setStudentId("A7654321X");
        secondDescriptor.setClassId("B02");

        StudentDetailContainsQueryPredicate firstPredicate = new StudentDetailContainsQueryPredicate(firstDescriptor);
        StudentDetailContainsQueryPredicate secondPredicate = new StudentDetailContainsQueryPredicate(secondDescriptor);
        // same object -> returns true
        assertEquals(firstPredicate, firstPredicate);

        // same values -> returns true
        StudentDetailContainsQueryPredicate firstPredicateCopy =
                new StudentDetailContainsQueryPredicate(firstDescriptor);
        assertEquals(firstPredicate, firstPredicateCopy);

        // different types -> returns false
        assertNotEquals(1, firstPredicate);

        // null -> returns false
        assertNotEquals(null, firstPredicate);

        // different student -> returns false
        assertNotEquals(firstPredicate, secondPredicate);
    }

    @Test
    public void test_detailsContainsQueryWords_returnsTrue() {
        // query words in predicate matches name
        StudentDetailContainsQueryPredicate predicate = buildPredicate("Alice Bob", null, null);
        assertTrue(predicate.test(new StudentBuilder()
                .withName("Alice Bob")
                .withStudentId("A0123456X")
                .withClassId("B01")
                .build()));

        // query words in predicate matches student id
        predicate = buildPredicate(null, "A0123456X", null);
        assertTrue(predicate.test(new StudentBuilder()
                .withName("Alice Bob")
                .withStudentId("A0123456X")
                .withClassId("B01")
                .build()));

        // query words in predicate matches class id
        predicate = buildPredicate(null, null, "B01");
        assertTrue(predicate.test(new StudentBuilder()
                .withName("Alice Bob")
                .withStudentId("A0123456X")
                .withClassId("B01")
                .build()));

        // query words in predicate matches multiple fields
        predicate = buildPredicate("Alice Bob", "A0123456X", "B01");
        assertTrue(predicate.test(new StudentBuilder()
                .withName("Alice Bob")
                .withStudentId("A0123456X")
                .withClassId("B01")
                .build()));

        // query words in predicate matches name partially
        predicate = buildPredicate("Alice", null, null);
        assertTrue(predicate.test(new StudentBuilder()
                .withName("Alice Bob")
                .withStudentId("A0123456X")
                .withClassId("B01")
                .build()));

        // query words in predicate matches cid and sid in mixed-case and partially
        predicate = buildPredicate(null, "a0123", "b01");
        assertTrue(predicate.test(new StudentBuilder()
                .withName("Alice Bob")
                .withStudentId("A0123456X")
                .withClassId("B01")
                .build()));
    }

    @Test
    public void test_detailsDoesNotContainQueryWords_returnsFalse() {
        // Non-matching keyword
        StudentDetailContainsQueryPredicate predicate = buildPredicate("Carol", null, null);
        assertFalse(predicate.test(new StudentBuilder().withName("Alice Bob").build()));

        // Keywords match cid and sid, but does not match name
        predicate = buildPredicate("Peter", "A0214212H", "B01");
        assertFalse(predicate.test(new StudentBuilder().withName("Alice").withStudentId("A0214212H")
                .withClassId("B01").build()));
    }

    private StudentDetailContainsQueryPredicate buildPredicate(String name, String sid, String cid) {
        QueryStudentDescriptor descriptor = new QueryStudentDescriptor();
        descriptor.setName(name);
        descriptor.setStudentId(sid);
        descriptor.setClassId(cid);
        return new StudentDetailContainsQueryPredicate(descriptor);
    }
}