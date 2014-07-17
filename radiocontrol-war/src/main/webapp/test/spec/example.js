describe("A suite", function() {
  it("contains spec with an expectation", function() {
    expect(true).toBe(true);
  });
});

function add(a, b) {
    return a + b;
}

describe("Addition", function() {
    it("should be possible to add 2 and 6 and the sum is 8", function() {
        expect(add(2, 6)).toBe(8);
    });
});