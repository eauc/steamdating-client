require "httpclient"

Before('@online') do
  client = HTTPClient.new
  client.delete("http://localhost:4000/test/tournaments/all")
end

Given("I am logged in") do
  page.execute_script("steamdating.services.online.test_login('eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImhhaWhhc3R1ckB5YWhvby5mciIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJwZXJtaXNzaW9ucyI6WyJ1c2VyOmRlZmF1bHQiLCJ1c2VyOnRvdXJuYW1lbnRfb3JnYW5pemVyIl0sImlzcyI6Imh0dHBzOi8vZWF1Yy5ldS5hdXRoMC5jb20vIiwic3ViIjoiYXV0aDB8NTc3ZTVmZDA0ZTU3MGU0ZjdmMTc0OTk0IiwiYXVkIjoiQ0tHRzliV2YxVUp2d1RyVTBZYThWOHRVQ043dksyN0MifQ.-77UUxqk7jo8mA0lBPkgMfHuD4pljlpW4FcYXHiUfmM')")
end

When("I upload current tournament:") do |table|
  @page.upload_tournament(table.hashes[0])
  expect_toaster("Tournament uploaded")
end

Then("I can see the online tournament in the list:") do |table|
  @page.expect_online_tournament(table.hashes[0])
end

When("I download the online tournament \"$name\"") do |name|
  @page.download_online_tournament(name)
  expect_toaster("Online tournament loaded")
end
