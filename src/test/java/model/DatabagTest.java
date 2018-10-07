package model;

import harnesses.ResourceLoader;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static junit.framework.TestCase.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by dsanchez on 12/2/17.
 */
public class DatabagTest {

    @Test
    public void testShouldRetrieveDeeplyHeldValue_1() throws IOException {
        JSONObject complexJSON = new JSONObject( ResourceLoader.getFileAsString("json_with_nested_keys.json") );

        Integer actual = Databag.getTyped(complexJSON.toMap(), "nested_1.nested_2.key");
        assertEquals( new Integer(1), actual);
    }

    @Test
    public void testShouldRetrieveShallowValue() throws IOException {
        JSONObject complexJSON = new JSONObject( ResourceLoader.getFileAsString("json_with_nested_keys.json") );

        Map expected = new HashMap<String,Object>(){{
            put("nested_2", new HashMap<String,Object>(){{
                put("key", 1);
            }});
        }};
        assertEquals( expected, Databag.get(complexJSON.toMap(), "nested_1"));
    }

    @Test
    public void testShouldRetrieveSimplestValue() throws IOException {
        JSONObject complexJSON = new JSONObject( ResourceLoader.getFileAsString("json_with_nested_keys.json") );

        assertEquals( "abcd", Databag.get(complexJSON.toMap(),"simplest"));
    }

    @Test
    public void testShouldRetrieveNullIfPathIsEmpty() throws IOException {
        JSONObject complexJSON = new JSONObject( ResourceLoader.getFileAsString("json_with_nested_keys.json") );

        assertNull(Databag.get(complexJSON.toMap(),""));
    }

    @Test
    public void testShouldRetrieveNullIfKeyDoesntExist() throws IOException {
        JSONObject complexJSON = new JSONObject( ResourceLoader.getFileAsString("json_with_nested_keys.json") );

        assertNull(Databag.get(complexJSON.toMap(),"unknown"));
    }

    @Test
    public void testShouldRetrieveDefaultValueIfValueOfKeyIsNull() throws IOException {
        JSONObject complexJSON = new JSONObject( ResourceLoader.getFileAsString("json_with_nested_keys.json") );

        assertEquals(9, Databag.getOrDefault(complexJSON.toMap(),"unknown", 9));
    }

    @Test
    public void testShouldRetrieveValue() throws IOException {
        JSONObject complexJSON = new JSONObject( ResourceLoader.getFileAsString("json_with_nested_keys.json") );

        assertEquals("abcd",     Databag.getOrDefault(complexJSON.toMap(),"simplest", 9));
    }

    @Test
    public void testShouldRetrieveNewDatabagWithPickedKeys() throws IOException {
        JSONObject complexJSON = new JSONObject( ResourceLoader.getFileAsString("pick_from.json") );

        Map actual = new HashMap();
        Databag.pick(complexJSON.toMap(), actual, "c", "nested.nested.nested_key");

        Map expected = new HashMap(){{
            put("c", true);
            put("nested_key", 2);
        }};

        assertEquals(expected, actual);
    }

    @Test
    public void testShouldRetrieveTypedValue() throws IOException {
        JSONObject complexJSON = new JSONObject( ResourceLoader.getFileAsString("arrays.json") );

        List<Integer> expected = Arrays.asList( new Integer[]{1,2,3} );
        List<Integer> actual = Databag.getTyped(complexJSON.toMap(), "array");

        assertEquals(expected, actual);
    }

    @Test
    public void testShouldRetrieveDefaultTypedValueIfNull() throws IOException {
        JSONObject complexJSON = new JSONObject( ResourceLoader.getFileAsString("arrays.json") );

        List<Integer> expected = Arrays.asList( new Integer[]{1} );
        List<Integer> actual = Databag.getTypedOrDefault( complexJSON.toMap(), "null_array",
                Arrays.asList( new Integer[]{1} ));
        assertEquals(expected,actual);
    }

    @Test
    public void testShouldRetrieveValueTyped() throws IOException {
        JSONObject complexJSON = new JSONObject( ResourceLoader.getFileAsString("arrays.json") );

        List<Integer> expected = Arrays.asList();
        List<Integer> actual = Databag.getTypedOrDefault( complexJSON.toMap(), "empty_array",
                Arrays.asList( new Integer[]{1} ));
        assertEquals(expected,actual);
    }

    @Test
    public void testShouldRenameKeyAndReturnNewInstanceWithKeyRenamed() throws IOException {
        JSONObject complexJSON = new JSONObject( ResourceLoader.getFileAsString("key_renaming.json") );

        HashMap actual = new HashMap(complexJSON.toMap());
        Databag.renameKey(actual, "d", "new_d");

        HashMap expected = new HashMap(){{
            put("new_d", 9.2);
            put("nested", new HashMap<String,Object>(){{
                put("nested_key", 2);
            }});
        }};

        assertEquals(expected, actual);
    }

    @Test
    public void testShouldLeaveKeyUnchangedWhenRenamed() throws IOException {
        JSONObject complexJSON = new JSONObject( ResourceLoader.getFileAsString("key_renaming.json") );

        HashMap actual = new HashMap(complexJSON.toMap());
        Databag.renameKey(actual, "d", "d");

        HashMap expected = new HashMap(){{
            put("d", 9.2);
            put("nested", new HashMap<String,Object>(){{
                put("nested_key", 2);
            }});
        }};

        assertEquals(expected, actual);
    }

    @Test
    public void testShouldRenameNestedKeyAndReturnNewInstanceWithKeyRenamed() throws IOException {
        JSONObject complexJSON = new JSONObject( ResourceLoader.getFileAsString("key_renaming.json") );

        HashMap actual = new HashMap(complexJSON.toMap());
        Databag.renameKey(actual,"nested.nested_key", "nested.new_nested_key");

        HashMap expected = new HashMap<String,Object>(){{
            put("d", 9.2);
            put("nested", new HashMap<String,Object>(){{
                put("new_nested_key", 2);
            }});
        }};

        assertEquals(expected, actual);
    }

    @Test
    public void testShouldNotRenameNestedKeyWhetherItDoesntExist() throws IOException {
        JSONObject complexJSON = new JSONObject( ResourceLoader.getFileAsString("key_renaming.json") );

        HashMap actual = new HashMap(complexJSON.toMap());
        Databag.renameKey(actual,"d.x.a", "d.x.h");

        HashMap expected = new HashMap<String,Object>(){{
            put("d", 9.2);
            put("nested", new HashMap<String,Object>(){{
                put("nested_key", 2);
            }});
        }};

        assertEquals(expected, actual);
    }

    @Test
    public void testShouldIterateAnArrayExecutingLambdaTwice() throws IOException {
        JSONObject complexJSON = new JSONObject( ResourceLoader.getFileAsString("arrays.json") );

        Consumer doSomething = Mockito.mock(Consumer.class);

        Databag.doOn(complexJSON.toMap(), "array", doSomething);

        verify(doSomething, times(3));

    }

    @Test
    public void testShouldIterateAnArrayExecutingNoLambdaBecauseValueIsNull() throws IOException {
        JSONObject complexJSON = new JSONObject( ResourceLoader.getFileAsString("arrays.json") );

        Consumer doSomething = Mockito.mock(Consumer.class);

        Databag.doOn(complexJSON.toMap(), "null_array", doSomething);

        verify(doSomething, never()).accept(null);

    }

    @Test
    public void testShouldIterateAnArrayExecutingNoLambdaBecauseArrayIsEmpty() throws IOException {
        JSONObject complexJSON = new JSONObject( ResourceLoader.getFileAsString("arrays.json") );

        Consumer doSomething = Mockito.mock(Consumer.class);

        Databag.doOn(complexJSON.toMap(), "empty_array", doSomething);

        verify(doSomething, never()).accept(null);

    }

    @Test
    public void testShouldIterateAnArrayExecutingNoLambdaBecauseAttributeIsNotAnArray() throws IOException {
        JSONObject complexJSON = new JSONObject( ResourceLoader.getFileAsString("arrays.json") );

        Consumer doSomething = Mockito.mock(Consumer.class);

        Databag.doOn(complexJSON.toMap(), "not_an_array", doSomething);

        verify(doSomething, never()).accept(null);

    }

    @Test
    public void testShouldTransformValueForGivenKeyFrom9ToDouble() throws IOException {
        HashMap actual = new HashMap(
            new JSONObject( ResourceLoader.getFileAsString("transformation.json") ).toMap()
        );
        Databag.transformValue(actual,"value0", v -> Math.pow((Integer)v, 2) );

        HashMap expected = new HashMap(){{
            put("value0", 81.0);
        }};assertEquals(expected, actual);
    }

    @Test
    public void testShouldChangeValueForKey() throws IOException {
        HashMap actual = new HashMap(
            new JSONObject( ResourceLoader.getFileAsString("value_alteration.json") ).toMap()
        );
        Databag.put(actual,"value0.value1.value", 9);

        HashMap expected = new HashMap(){{
            put("value0", new HashMap(){{
                put("value1", new HashMap(){{
                    put("value", 9);
                }});
            }});
        }};
        assertEquals(expected, actual);
    }

    @Test
    public void testShouldRemoveKey() throws IOException {
        HashMap actual = new HashMap(
            new JSONObject( ResourceLoader.getFileAsString("key_remotion.json") ).toMap()
        );
        Databag.removeStatefull(actual, "value0");

        HashMap expected = new HashMap(){{
            put("value1", 1);
        }};
        assertEquals(expected, actual);
    }

    @Test
    public void testShouldRemoveNestedKey() throws IOException {
        HashMap actual = new HashMap(
            new JSONObject( ResourceLoader.getFileAsString("key_remotion_nested.json") ).toMap()
        );
        Databag.removeStatefull(actual, "value1.removed");

        HashMap expected = new HashMap(){{
            put("value0", 0);
            put("value1", new HashMap(){{
                put("value", "abc");
            }});
        }};

        assertEquals(expected, actual);
    }

    @Test
    public void testShouldRemoveNoKey() throws IOException {
        HashMap actual = new HashMap(
            new JSONObject( ResourceLoader.getFileAsString("key_remotion_nested.json") ).toMap()
        );
        Databag.removeStatefull(actual, "value1.removed.x.x.x" /* invalid key */);

        HashMap expected = new HashMap(){{
            put("value0", 0);
            put("value1", new HashMap(){{
                put("value", "abc");
                put("removed", 1);
            }});
        }};

        assertEquals(expected, actual);
    }



}
