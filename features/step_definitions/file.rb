Given("I open File page") do
  @page = Pages::File.new
            .load
end

When("I import T3 CSV players file \"$file\"") do |file|
  @page.open_t3_csv(file)
end
