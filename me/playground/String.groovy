
def s2 = 'billryan'
println "'billryan'.length(): ${s2.length()}"
assert s2.length() == 8
println "'billryan'.size(): ${s2.size()}"
assert s2.size() == 8
println "'billryan'.substring(4, 8): ${s2.substring(4, 8)}"
assert s2.substring(4, 8) == 'ryan'
println "'billryan'.substring(4, 4): ${s2.substring(4, 4)}"
assert s2.substring(4, 4) == ''

char[] chars = s2.toCharArray()
println "'billryan'.toCharArray().length: ${chars.length}"
assert chars.length == 8
println "'billryan'.toCharArray().size(): ${chars.size()}"
assert chars.size() == 8

println "'billryan'.charAt(4): ${s2.charAt(4)}"
assert s2.charAt(4) == 'r'

println "'billryan'.indexOf('r'): ${s2.indexOf('r')}"
assert s2.indexOf('r') == 4

println "'billryan'.indexOf('x'): ${s2.indexOf('x')}"
assert s2.indexOf('x') == -1

