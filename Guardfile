guard :shell do
  watch(/.+\.java/) do |m|
    title = 'Compile'
    msg = `make`
    status = ($?.success? && :success) || :failed

    n msg, title, status
    "-> #{msg}"
  end
end
